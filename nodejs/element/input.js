// 내부모듈 불러오기
const readline = require('readline');

// 입출력 인터페이스 만들기
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});

// 입력받은 후 name 변수에 저장
rl.question('이름: ', (name) => {
    rl.question('나이: ', (age) => {
        rl.question('도시: ', (city) => {
            console.log('이름: ', name);
            console.log('나이: ', age);
            console.log('도시: ', city);
            console.log(`${city}에 사는 ${age}살 ${name}님, 반갑습니다!`);
            rl.close();
        });
    });
});