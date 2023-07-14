const btnCadastrar = document.getElementById("btnCadastrar");
const spinSave = document.getElementById("spinSave");

const spinBtnSave = (bool) => {
  if (bool) {
    spinSave.classList.add("ativo");
    btnCadastrar.disabled = true;
  } else {
    spinSave.classList.remove("ativo");
    btnCadastrar.disabled = false;
  }
};

export {spinBtnSave}
