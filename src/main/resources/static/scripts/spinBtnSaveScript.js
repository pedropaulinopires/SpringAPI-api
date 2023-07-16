const spinSave = document.getElementById("spinSave");

const spinActive = (bool) => {
  if (bool) {
    spinSave.classList.add("ativo");
  } else {
    spinSave.classList.remove("ativo");
  }
};

export {spinActive}
