

document.querySelectorAll("input").forEach((input) => {
  input.addEventListener("focus", () => {
    input.labels.forEach((label) => {
      if (input.value.length == 0) {
        label.classList.add("active");
      }
    });
  });

  input.addEventListener("blur", () => {
    input.labels.forEach((label) => {
      if (input.value.length == 0) {
        label.classList.remove("active");
      }
    });
  });
});

document.getElementById("eyeNot").addEventListener("click", function () {
  document.getElementById("eyeNot").style.display = "none";
  document.getElementById("eyeOk").style.display = "block";
  document.getElementById("password").setAttribute("type", "text");
});

document.getElementById("eyeOk").addEventListener("click", function () {
  document.getElementById("eyeOk").style.display = "none";
  document.getElementById("eyeNot").style.display = "block";
  document.getElementById("password").setAttribute("type", "password");
});

document.getElementById("btnSumbmit").addEventListener("click", sumbmit);

function sumbmit() {
  const password = document.getElementById("password");
  const username = document.getElementById("username");
  const label_password = document.getElementById("labelPassword");
  const label_username = document.getElementById("usernameLabel");
  const msg_password = document.getElementById("passwordNull");
  const msg_username = document.getElementById("userNull");
  if (password.value.length == 0 && username.value.length == 0) {
    msg_password.classList.add("active");
    msg_password.innerText = "Campo de senha obrigatório!";
    msg_username.classList.add("active");

    username.classList.add("error");
    password.classList.add("error");

    label_password.classList.add("error");
    label_username.classList.add("error");
  } else if (username.value.length == 0) {
    msg_password.classList.remove("active");
    msg_username.classList.add("active");

    username.classList.add("error");
    password.classList.remove("error");

    label_password.classList.remove("error");
    label_username.classList.add("error");
  } else if (password.value.length == 0) {
    msg_password.classList.add("active");
    msg_password.innerText = "Campo de senha obrigatório!";
    msg_username.classList.remove("active");

    username.classList.remove("error");
    password.classList.add("error");

    label_password.classList.add("error");
    label_username.classList.remove("error");
  } else {
    let user = {
      username: username.value,
      password: password.value,
    };
    const endpoint = "http://localhost:8080/user/auth";
    const headers = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    };
    fetch(endpoint, headers).then(function (response) {
      if (response.status == 401) {
        msg_password.classList.add("active");
        msg_password.innerText = "Usuario/senha incorretos!";
        msg_username.classList.remove("active");

        username.classList.remove("error");
        password.classList.remove("error");

        label_password.classList.remove("error");
        label_username.classList.remove("error");
      } else {
        msg_password.classList.remove("active");
        msg_username.classList.remove("active");

        username.classList.remove("error");
        password.classList.remove("error");

        label_password.classList.remove("error");
        label_username.classList.remove("error");

        document.body.style.pointerEvents = "none";
        location.href("/contatos.html");
      }
    });
  }
}

addEventListener("keypress", function (element) {
  if (element.key == "Enter") {
    sumbmit();
  }
});
