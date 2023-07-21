const btnCloseModalRemove = document.getElementById("btnCloseModalRemove");
const btnRemove = document.getElementById("btnRemove");
const msgModaRemove = document.getElementById("msgModalRemove");
const spinRemove = document.getElementById("spinRemove");

import { loadPeoplesAfterDelete } from "./getPeoplesSetTable.js";

let idPersonDelete;


const spinRemoveActive = (bool) => {
  if (bool) {
    msgModaRemove.style.display = "none";
    spinRemove.classList.add("ativo");
  } else {
    msgModaRemove.style.display = "block";
    spinRemove.classList.remove("ativo");
  }
};

const deletePerson = () => {
  btnRemove.disabled = true;
  spinRemoveActive(true);
  const endpoint = `http://localhost:8080/people/${idPersonDelete}`
  const header = {
    method: "DELETE",
  };
  fetch(endpoint, header)
    .then((response) => {
      if(response.status == 401){
        //tente novamente
        
      } else if (response.ok) {
        loadPeoplesAfterDelete();
      }
    })
    .catch((err) => console.error(err));
};

const setDeletePerson = (ele) => {
  idPersonDelete = ele.target.getAttribute("data-idPerson");
};

btnRemove.addEventListener("click", deletePerson);

export { setDeletePerson, spinRemoveActive , btnCloseModalRemove , btnRemove };
