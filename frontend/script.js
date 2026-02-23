const BASE_URL = "http://localhost:8080/api/v1";

async function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch(BASE_URL + "/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    });

    if (!response.ok) {
        const error = await response.json();
        document.getElementById("message").innerText = error.message;
        return;
    }

    const data = await response.json();

    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);

    window.location.href = "dashboard.html";
}

async function register() {
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;

    const response = await fetch(BASE_URL + "/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password, role })
    });

    const text = await response.text();
    document.getElementById("message").innerText = text;
}

function loadDashboard() {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    document.getElementById("welcome").innerText = "Logged In Successfully";
    document.getElementById("roleDisplay").innerText = "Role: " + role;

    fetchTasks();
}

async function createTask() {
    const token = localStorage.getItem("token");

    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;

    await fetch(BASE_URL + "/tasks", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({ title, description })
    });

    fetchTasks();
}

async function fetchTasks() {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    const response = await fetch(BASE_URL + "/tasks", {
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    const tasks = await response.json();

    const list = document.getElementById("taskList");
    list.innerHTML = "";

    tasks.forEach(task => {
        const li = document.createElement("li");
        li.innerText = task.title + " - " + task.description + " (Owner: " + task.ownerName + ")";

        if (role === "ADMIN") {
            const delBtn = document.createElement("button");
            delBtn.innerText = "Delete";
            delBtn.onclick = () => deleteTask(task.id);
            li.appendChild(delBtn);
        }

        list.appendChild(li);
    });
}

async function deleteTask(id) {
    const token = localStorage.getItem("token");

    await fetch(BASE_URL + "/tasks/" + id, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    fetchTasks();
}

function logout() {
    localStorage.clear();
    window.location.href = "login.html";
}