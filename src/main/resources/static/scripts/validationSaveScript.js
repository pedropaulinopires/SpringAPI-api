const inome = document.getElementById("inome");
const isexoM = document.getElementById("isexoM");
const isexoF = document.getElementById("isexoF");
const msgSexo = document.getElementById("msgSexo");
const msgNome = document.getElementById("msgNome");

const validationSave = function () {
    msgNome.innerHTML = "Campo nome obrigatório!";
    if (
      inome.value.trim() == "" &&
      isexoM.checked == false &&
      isexoF.checked == false
    ) {
      inome.classList.add("is-invalid");
      msgSexo.classList.add("ativo");
      return false;
    } else if (
      inome.value.trim() != "" &&
      isexoM.checked == false &&
      isexoF.checked == false
    ) {
      inome.classList.remove("is-invalid");
      msgSexo.classList.add("ativo");
      return false;
    } else if (
      inome.value.trim() == "" &&
      (isexoM.checked == true || isexoF.checked == true)
    ) {
      inome.classList.add("is-invalid");
      msgSexo.classList.remove("ativo");
      return false;
    } else {
      inome.classList.remove("is-invalid");
      msgSexo.classList.remove("ativo");
      return true;
    }
  };
  
  export{validationSave}