const random = Math.floor(Math.random() * 100) + 1;

console.log('1부터 100 사이의 숫자: ', random);

if(random % 2 === 0) {
    console.log('짝수입니다.');
} else {
    console.log('홀수입니다.');
}

if(random > 50) {
    console.log('50보다 큽니다.');
} else {
    console.log('50 이하입니다.');
}