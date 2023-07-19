const inomeEdit = document.getElementById("inomeEdit");
const isexoEditM = document.getElementById("isexoEditM");
const isexoEditF = document.getElementById("isexoEditF");
const msgSexoEdit = document.getElementById("msgSexoEdit")
const msgEdit = document.getElementById("msgEdit");

const validationEdit = function () {
  msgEdit.innerHTML = "Campo nome obrigatório!";
  if (
    inomeEdit.value.trim() == "" &&
    isexoEditM.checked == false &&
    isexoEditF.checked == false
  ) {
    inomeEdit.classList.add("is-invalid");
    msgSexoEdit.classList.add("ativo");
    return false;
  } else if (
    inomeEdit.value.trim() != "" &&
    isexoEditM.checked == false &&
    isexoEditF.checked == false
  ) {
    inomeEdit.classList.remove("is-invalid");
    msgSexoEdit.classList.add("ativo");
    return false;
  } else if (
    inomeEdit.value.trim() == "" &&
    (isexoEditM.checked == true || isexoEditF.checked == true)
  ) {
    inomeEdit.classList.add("is-invalid");
    msgSexoEdit.classList.remove("ativo");
    return false;
  } else {
    inomeEdit.classList.remove("is-invalid");
    msgSexoEdit.classList.remove("ativo");
    return true;
  }
};

const modalEditReset = ()=>{
  inomeEdit.classList.remove("is-invalid")
  msgSexoEdit.classList.remove("ativo")
}

export { validationEdit , modalEditReset };
