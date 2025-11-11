import readline from 'readline/promises';
import fs from 'fs/promises';

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

rl.question('메모할 내용을 입력하세요: ')
.then((text) => fs.writeFile('memo.txt', text, 'utf8'))
.then(() => {
    console.lot('메모가 memo.txt에 저장되었습니다.');
})
.catch((err) => {
    console.error('파일 저장 중 오류: ', err.message);
})
.finally(() => rl.close());