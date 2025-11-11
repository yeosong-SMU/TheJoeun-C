import fs from 'fs/promises';

fs.readFile('user.json', 'utf8')
.then((data) => {
    const user = JSON.parse(data);
    console.log("사용자 정보: ");
    console.log('이름: ', user.name);
    console.log('나이: ', user.age);
    console.log('학교: ', user.school);
})
.catch((err) => {
    console.error("읽기 오류: ", err.message);
});