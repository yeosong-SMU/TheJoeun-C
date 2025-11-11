const numbers = [12, 5, 8, 21, 33, 4, 19];

const even = numbers.filter((n) => n % 2 === 0);
const odd = numbers.filter((n) => n % 2 !== 0);
const sorted = [...numbers].sort((a, b) => a - b);

console.log('원본 배열: ', numbers);
console.log('짝수: ', even);
console.log('홀수: ', odd);
console.log('정렬된 배열: ', sorted);