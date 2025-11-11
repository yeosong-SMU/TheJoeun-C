let a = ['apple', 'banana', 'cherry'];

let b = a;  //얕은 복사
b.push('grape');

console.log('원본 a: ', a);   //a도 바뀜
console.log('b: ', b);

a = ['apple', 'banana', 'cherry'];
b = [...a];  //깊은 복사
b.push('orange');

console.log('원본 a: ', a);   //그대로 유지됨
console.log('b: ', b);