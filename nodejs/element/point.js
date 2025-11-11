import readline from 'readline/promises';
import { stdin as input, stdout as output } from 'node:process';

const rl = readline.createInterface({ input, output });

rl.question('점수를 입력하세요 (0~100): ')
.then((input) => {
    const score = Number(input);
    if(score >= 90){
        console.log('A 학점입니다.');
    } else if(score >= 80){
        console.log('B 학점입니다.');
    } else if(score >= 70){
        console.log('C 학점입니다.');
    } else if(score >= 60){
        console.log('D 학점입니다.');
    } else {
        console.log('F 학점입니다.');
    }
})
.catch((err) => {
    console.error('입력 처리 중 오류 발생: ', err.message);
})
.finally(() => rl.close());