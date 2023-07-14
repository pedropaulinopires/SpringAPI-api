const inome = document.getElementById("inome");
const isexoM = document.getElementById("isexoM");
const msgNome = document.getElementById("msgNome");

const btnCadastrar = document.getElementById("btnCadastrar");
const spinSave = document.getElementById("spinSave");

import { validationSave } from "./validationSaveScript.js";
import { showAlerta } from "./msgAlertaScript.js";
import { spinBtnSave } from "./spinBtnSaveScript.js";

let person = {
  name: "",
  sexo: "",
  setPersonSave: function () {
    this.name = inome.value;
    if (isexoM.checked == true) {
      this.sexo = "M";
    } else {
      this.sexo = "F";
    }
  },
};


const savePerson = () => {
  if (validationSave()) {
    spinBtnSave(true);
    const endpoint = "http://ec2-3-85-188-228.compute-1.amazonaws.com:8080/people/save";
    person.setPersonSave()
    const headers = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(person),
    };
    fetch(endpoint, headers)
      .then(function (response) {
        spinBtnSave(false);
        if (response.status == 500) {
          inome.classList.add("is-invalid");
          msgNome.innerHTML = "Contato com esse nome já existe, tente outro!";
        } else {
          inome.value = "";
          showAlerta("Contato salvo com sucesso!");
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }
};

btnCadastrar.addEventListener("click", savePerson);

addEventListener("keypress", (evt) => {
  if (evt.key == "Enter") {
    savePerson();
  }
});
