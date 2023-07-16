const spinEdit = document.getElementById("spinEdit");

const spinEditActive = (bool) => {
  if (bool) {
    spinEdit.classList.add("ativo");
  } else {
    spinEdit.classList.remove("ativo");
  }
};

export { spinEditActive };
