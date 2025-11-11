const now = new Date();

console.log('날짜: ', now.toLocaleDateString());
console.log('시간: ', now.toLocaleTimeString());

const hour = now.getHours();

if(hour < 12){
    console.log('좋은 아침입니다!');
}else if (hour < 18){
    console.log('좋은 오후입니다!');
}else{
    console.log('좋은 저녁입니다!');
}