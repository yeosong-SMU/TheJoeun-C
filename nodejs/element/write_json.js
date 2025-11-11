import fs from 'fs/promises';

const user = {
    name: '김철수',
    age: 16,
    school: '신일고',
};

fs.writeFile('user.json', JSON.stringify(user, null, 2), 'utf8')
.then(() => {
    console.log('user.json 파일이 저장되었습니다.');
})
.catch((err) => {
    console.error('저장 오류: ', err);
});