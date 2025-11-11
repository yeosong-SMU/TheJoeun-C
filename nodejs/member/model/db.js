import mysql from "mysql2";

const pool = mysql.createPool({
    host: "localhost",
    user: "user",
    password: "1234",
    database: "web",
});

export default pool.promise();