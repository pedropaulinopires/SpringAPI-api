const inomeEdit = document.getElementById("inomeEdit");
const isexoEditM = document.getElementById("isexoEditM");
const isexoEditF = document.getElementById("isexoEditF");
const closeModaEdit = document.getElementById("closeModaEdit");
const salvarEditar = document.getElementById("salvarEditar");
const msgEdit = document.getElementById("msgEdit");

import { validationEdit, modalEditReset } from "./validationEditScript.js";
import { spinEditActive } from "./spinBtnEditScript.js";
let idEditPerson;
import { loadPeoplesAfterEdit } from "./getPeoplesSetTable.js";

inomeEdit.addEventListener("keypress", (evt) => {
  if (evt.key == "Enter") {
    evt.preventDefault();
    editPerson()
  }
});

const setEditPerson = (ele) => {
  idEditPerson = ele.target.getAttribute("data-idPerson");
  inomeEdit.value = ele.target.getAttribute("data-namePerson");
  if (ele.target.getAttribute("data-sexPerson") == "M") {
    isexoEditM.checked = true;
    isexoEditF.checked = false;
  } else {
    isexoEditM.checked = false;
    isexoEditF.checked = true;
  }
  validationEdit();
};

let person = {
  id: "",
  name: "",
  sexo: "",
  setPersonEdit: function () {
    this.id = idEditPerson;
    this.name = inomeEdit.value;
    if (isexoEditM.checked == true) {
      this.sexo = "M";
    } else {
      this.sexo = "F";
    }
  },
};

const editPerson = () => {
  if (validationEdit()) {
    spinEditActive(true);
    salvarEditar.disabled = true;
    const endpoint =
      "http://localhost:8080/people/replace";
    person.setPersonEdit();
    const headers = {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(person),
    };
    fetch(endpoint, headers)
      .then(function (response) {
        if (response.status == 500) {
          inomeEdit.classList.add("is-invalid");
          msgEdit.innerHTML = "Contato com esse nome já existe, tente outro!";
          salvarEditar.disabled = false;
          spinEditActive(false);
        } else if(response == 401){
          //tente novamente
        } else {
          loadPeoplesAfterEdit();
        }
      })
      .catch((err) => {
        console.error(err);
        spinEditActive(false);
      });
  }
};

salvarEditar.addEventListener("click", editPerson);

closeModaEdit.addEventListener("click", () => {
  modalEditReset();
});
export { setEditPerson, closeModaEdit, salvarEditar };
