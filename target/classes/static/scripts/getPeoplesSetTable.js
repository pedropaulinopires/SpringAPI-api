const corpoTabela = document.getElementById("corpoTabela");
const estruturaTabela = document.getElementById("estruturaTabela");
const msgAlerta2 = document.getElementById("msgAlerta2");

import { spinActive } from "./spinBtnSaveScript.js";
import { setEditPerson , salvarEditar , closeModaEdit } from "./editPersonScript.js";
import { setDeletePerson , spinRemoveActive , btnCloseModalRemove, btnRemove } from "./deletePersonScript.js";
import { showAlerta } from "./msgAlertaScript.js";
import { spinEditActive } from "./spinBtnEditScript.js";
import { servicePagination } from "./paginationScript.js";

let btnRemovePerson;
let btnEditPerson;

const appendPersonTable = (resp) => {
  [...corpoTabela.children].map((ele) => {
    ele.remove();
  });
  resp.content.map((ele) => {
    let tr = document.createElement("tr");
    tr.innerHTML = `<td>${ele.name}</td>
        <td>${ele.sexo}</td>
        <td> <button type="button" class="btn btn-primary btnEditPerson" data-bs-toggle="modal" data-bs-target="#modalEdit" data-idPerson="${ele.id}" data-namePerson="${ele.name}" data-sexPerson="${ele.sexo}">Editar</button>  <button type="button" class="btn btn-danger btnRemovePerson" data-idPerson="${ele.id}" data-bs-toggle="modal" data-bs-target="#modalRemove">Excluir</button></td>
        `;
    corpoTabela.appendChild(tr);
  });
  btnRemovePerson = [...document.getElementsByClassName("btnRemovePerson")];
  btnRemovePerson.map((ele) => {
    ele.addEventListener("click", (ele) => {
      ele.preventDefault();
      setDeletePerson(ele);
    });
  });

  btnEditPerson = [...document.getElementsByClassName("btnEditPerson")];
  btnEditPerson.map((ele) => {
    ele.addEventListener("click", (ele) => {
      ele.preventDefault();
      setEditPerson(ele);
    });
  });
};

const showAndHideTable = (bool) => {
  if (bool) {
    estruturaTabela.style.display = "none";
    msgAlerta2.classList.add("ativo");
  } else {
    estruturaTabela.style.display = "block";
    msgAlerta2.classList.remove("ativo");
  }
};
const showAndHideTableNotMessage = (bool) => {
  if (bool) {
    estruturaTabela.style.display = "none";
  } else {
    estruturaTabela.style.display = "block";
  }
};
const loadPeoples = (page = 1) => {
  spinActive(true);
  const endpoint =
    "http://localhost:8080/peoples?page=" + page;
  const header = {
    method: "GET",
  };
  fetch(endpoint, header)
    .then((response) => {
      if (!response.ok) {
        console.log(response);
      } else {
        spinActive(false);
        response.json().then((resp) => {
          if (resp.totalElements != 0) {
            showAndHideTable(false);
            appendPersonTable(resp);
            servicePagination(resp)
          } else {
            showAndHideTable(true);
          }
        });
      }
    })
    .catch((err) => console.error(err));
};

const loadPeoplesAfterDelete = (page=1) => {
  const endpoint =
    "http://localhost:8080/peoples?page=" + page;
  const header = {
    method: "GET",
  };
  fetch(endpoint, header)
    .then((response) => {
      if (!response.ok) {
        console.log(response);
      } else {
        response.json().then((resp) => {
          if (resp.totalElements != 0) {
            showAndHideTable(false);
            appendPersonTable(resp);
            servicePagination(resp)
          } else {
            showAndHideTable(true);
          }
        });
      }
    })
    .catch((err) => console.error(err));
    spinRemoveActive(false)
    btnCloseModalRemove.click()
    btnRemove.disabled = false
    showAlerta("Contato removido com sucesso!")
};

const loadPeoplesAfterEdit = (page=1) => {
  const endpoint =
    "http://localhost:8080/peoples?page=" + page;
  const header = {
    method: "GET",
  };
  fetch(endpoint, header)
    .then((response) => {
      if (!response.ok) {
        console.log(response);
      } else {
        response.json().then((resp) => {
          if (resp.totalElements != 0) {
            showAndHideTable(false);
            appendPersonTable(resp);
            servicePagination(resp)
          } else {
            showAndHideTable(true);
          }
        });
      }
    })
    .catch((err) => console.error(err));
    spinEditActive(false)
    closeModaEdit.click()
    salvarEditar.disabled = false
    showAlerta("Contato editado com sucesso!")
};

loadPeoples();


export { loadPeoples, showAndHideTableNotMessage, btnRemovePerson, loadPeoplesAfterDelete, loadPeoplesAfterEdit };
