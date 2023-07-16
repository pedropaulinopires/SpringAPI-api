const msgAlerta = document.getElementById("msgAlerta");

let timeAlert;
const showAlerta = (msg) => {
  [...msgAlerta.children].map((ele) => {
    ele.innerHTML = msg;
  });
  msgAlerta.classList.add("ativo");
  if (msgAlerta.classList.contains("ativo")) {
    clearTimeout(timeAlert);
    timeAlert = setTimeout(function () {
      msgAlerta.classList.remove("ativo");
    }, 3000);
  } else {
    timeAlert = setTimeout(function () {
      msgAlerta.classList.remove("ativo");
    }, 3000);
  }
};

export { showAlerta };
