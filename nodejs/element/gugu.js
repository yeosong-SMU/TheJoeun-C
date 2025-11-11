import readline from 'readline/promises';
import { stdin as input, stdout as output } from 'node:process';

const rl = readline.createInterface({ input, output });

rl.question('단을 입력하세요: ').then(answer => {
    const d = +answer;
    if(d < 1 || d > 9) {
        console.log('1부터 9 사이 숫자를 입력하세요. ');
    } else {
        for (let i = 1; i <= 9; i++) {
            console.log(`${d} x ${i} = ${d*i}`);
        }
    }
    rl.close();
});