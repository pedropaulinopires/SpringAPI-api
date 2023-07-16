const navegacao = document.getElementById("navegacao");
const pagination = document.getElementById("pagination");
import { loadPeoples } from "./getPeoplesSetTable.js";

//resp.totalPages
//resp.pageable.pageNumber

const deleteAllElementsPage = () => {
  [...pagination.children].map((ele) => {
    ele.remove();
  });
};

const pagePreviuos = () => {
  pagination.innerHTML += `<li class="page-item" id="previous">
      <a class="page-link" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>`;
};

const pageNext = () => {
  pagination.innerHTML += `<li class="page-item" id="next">
  <a class="page-link" aria-label="Next">
    <span aria-hidden="true">&raquo;</span>
  </a>
</li>`;
};

const servicePagination = (page) => {
  deleteAllElementsPage();
  if (page.totalPages > 1) {
    let currentPage = page.pageable.pageNumber + 1;
    let pageSize = page.totalPages;

    if (currentPage > 1) {
      pagePreviuos();
    }

    if (currentPage < 3) {
      if(pageSize <= 3){
        for (let c = 1; c <= pageSize; c++) {
          pagination.innerHTML += `<li class="page-item pageLink ${
            currentPage == c ? "active" : ""
          }"  ><a class="page-link" data-page="${c}">${c}</a></li>`;
        }
      } else {
        for (let c = 1; c <= 3; c++) {
          pagination.innerHTML += `<li class="page-item pageLink ${
            currentPage == c ? "active" : ""
          }"  ><a class="page-link" data-page="${c}">${c}</a></li>`;
        }
      }
      
    } else {
      if (pageSize - currentPage > 0) {
        pagination.innerHTML += `<li class="page-item pageLink "  ><a class="page-link" data-page="${
          currentPage - 1
        }">${currentPage - 1}</a></li>`;
        pagination.innerHTML += `<li class="page-item pageLink active"  ><a class="page-link" data-page="${currentPage}">${currentPage}</a></li>`;
        pagination.innerHTML += `<li class="page-item pageLink"  ><a class="page-link" data-page="${
          currentPage + 1
        }">${currentPage + 1}</a></li>`;
      } else {
        pagination.innerHTML += `<li class="page-item pageLink "  ><a class="page-link" data-page="${
          currentPage - 2
        }">${currentPage - 2}</a></li>`;
        pagination.innerHTML += `<li class="page-item pageLink "  ><a class="page-link" data-page="${
          currentPage - 1
        }">${currentPage - 1}</a></li>`;
        pagination.innerHTML += `<li class="page-item pageLink active"  ><a class="page-link" data-page="${currentPage}">${currentPage}</a></li>`;
      }
    }

    if (currentPage < pageSize) {
      pageNext();
    }

    let links = [...document.getElementsByClassName("pageLink")];
    links.map((ele) => {
      ele.addEventListener("click", function (evt) {
        loadPeoples(evt.target.getAttribute("data-page"));
      });
    });

    if (currentPage > 1) {
      document.getElementById("previous").addEventListener("click", () => {
        loadPeoples(--currentPage);
      });
    }

    if (currentPage < pageSize) {
      document.getElementById("next").addEventListener("click", () => {
        loadPeoples(++currentPage);
      });
    }
  }
};

export { servicePagination };
