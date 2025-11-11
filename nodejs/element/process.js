const args = process.argv.slice(2);  //인덱스 2부터
console.log('args: ', args);
if(args.length < 2){
    console.log('사용법: node process.js 이름 나이');
    process.exit(1);  //0 정상, 1 오류, >1 기타 비정상 종료
}
const [name, age] = args;
console.log('이름: ', name);
console.log('나이: ', age);
console.log(`환영합니다, ${name}님!`);
console.log(`당신은 ${age >= 20 ? '성인' : '미성년자'}입니다.`);