const inome = document.getElementById("inome");
const isexoM = document.getElementById("isexoM");
const msgNome = document.getElementById("msgNome");
const btnCadastrar = document.getElementById("btnCadastrar");

import { validationSave } from "./validationSaveScript.js";
import { showAlerta } from "./msgAlertaScript.js";
import { spinActive } from "./spinBtnSaveScript.js";
import { loadPeoples , showAndHideTableNotMessage } from "./getPeoplesSetTable.js";

inome.addEventListener("keypress",(evt)=>{
    if(evt.key == "Enter"){
        evt.preventDefault()
        savePerson()
    }
    
})

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
    spinActive(true)
    const endpoint = "http://localhost:8080/people/save";
    person.setPersonSave()
    const headers = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(person),
    };
    fetch(endpoint, headers)
      .then(function (response) {
        if (response.status == 500) {
          inome.classList.add("is-invalid");
          msgNome.innerHTML = "Contato com esse nome já existe, tente outro!";
          spinActive(false)
        } else if(response.status == 401){
          //tentar
        } else {
          inome.value = "";
          showAlerta("Contato salvo com sucesso!");
          showAndHideTableNotMessage(true)
          loadPeoples()
        }
      })
      .catch((err) => {
        console.error(err);
        spinActive(false)
      });
  }
};

btnCadastrar.addEventListener("click", savePerson);

