const msgAlerta = document.getElementById("msgAlerta");
const showAlerta = (msg) => {
    [...msgAlerta.children].map((ele) => {
      ele.innerHTML = msg;
    });
    msgAlerta.classList.add("ativo");
    setTimeout(function () {
      msgAlerta.classList.remove("ativo");
    }, 3000);
  };

  export {showAlerta}