// const btnModalImage :HTMLElement = document.getElementById("btn-modal-image");
// const btnChoseImage :HTMLElement = document.getElementById("btn-chose-image");
// const inputThumbnail :HTMLElement = document.getElementById("avatar");
// const btnDeleteImage :HTMLElement = document.getElementById("btn-delete-iamge");
// const imageContaimerEl :HTMLElement = document.getElementById(".image-container");
//
//
// const btnModalImage = document.getElementById("btn-modal-image");
// const btnChoseImage = document.getElementById("btn-chose-image");
// const inputThumbnailEl  = document.getElementById("avatar");
// const btnDeleteImage = document.getElementById("btn-delete-image");
// const imageContainerEl  = document.querySelector(".image-container");
//
// let imageList = [];
//
// btnModalImage.addEventListener("click", async () => {
//     try {
//         // Goi API
//         const data = await fetch("/api/v1/files");
//         const dataJson = await data.json();
//
//         // Lưu lại kết quả trả về từ server
//         imageList = dataJson;
//
//         // Hien thi hinh anh
//         renderPagination(imageList);
//     } catch (err) {
//         console.log(err)
//     }
// })
//
// // Hiển thị danh sách image
// function renderImages(imageList) {
//     // Xóa hết nd đang có trước khi render
//     imageContainerEl.innerHTML = "";
//
//     // Tạo nd
//     let html = ""
//     imageList.forEach(i => {
//         html += `
//             <div class="image-item">
//                 <img src="/api/v1/files/${i.id}" alt="">
//             </div>
//         `
//     })
//
//     // Insert nd
//     imageContainerEl.innerHTML = html;
// }
//
// // Hiển thị phần phân trang
// function renderPagination(imageList) {
//     $('.pagination-container').pagination({
//         dataSource: imageList,
//         pageSize : 12,
//         callback: function (data, pagination) {
//             renderImages(data);
//         }
//     })
// }