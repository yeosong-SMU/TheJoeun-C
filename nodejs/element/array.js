const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});

let numbers = [];

console.log('숫자를 5개 입력하세요');

function askNumber(i) {
    if ( i < 5) {
        rl.question(`${i + 1}번째 숫자: `, (num) => {
            numbers.push(Number(num));   //배열의 끝에 추가, 문자열을 숫자로 바꾼다
            askNumber(i + 1);
        });
    } else {
        const sum = numbers.reduce((a, b) => a + b, 0); //배열의 모든 값을 하나로 합친다. 초기값은 0
        const avg = sum / numbers.length;
        console.log('입력한 숫자: ', numbers);
        console.log('총합: ', sum);
        console.log('평균: ', avg.toFixed(2)); //소수 두자리
        rl.close();
    }
}

askNumber(0);