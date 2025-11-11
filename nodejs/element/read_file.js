import fs from 'fs/promises';

fs.readFile("memo.txt", "utf8")
.then((data) => {
    console.log("memo.txt 내용: ");
    console.log(data);
})
.catch((err) => {
    console.error("파일을 읽을 수 없습니다: ", err.message);
});