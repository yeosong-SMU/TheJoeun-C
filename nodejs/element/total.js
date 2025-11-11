import readline from 'readline/promises';
import { stdin as input, stdout as output } from 'node:process';

const rl = readline.createInterface({ input, output });

const scores = [];

function askScore(index) {
    return rl.question(`${index + 1}번쨰 점수 : `)
    .then((input) => {
        const score = parseFloat(input);
        if(!isNaN(score)) {
            scores.push(score);
        }

        if(index === 4){
            const total = scores.reduce((a, b) => a + b, 0);
            const avg = total / scores.length;
            console.log('점수: ', scores);
            console.log('총점: ', total);
            console.log('평균: ', avg.toFixed(2));
            rl.close();
        } else {
            return askScore(index + 1); //다음 질문으로 재귀
        }
    });
}

askScore(0)
.catch(err => {
    console.error("입력 중 오류 발생: ", err.message);
    rl.close();
});