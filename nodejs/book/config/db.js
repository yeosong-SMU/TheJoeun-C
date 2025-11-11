const oracledb = require('oracledb');
oracledb.outFormat = oracledb.OUT_FORMAT_OBJECT;

const getConnection = () => oracledb.getConnection({
    user: 'java',
    password: '1234',
    connectString: 'localhost/XE',
});

module.exports = { getConnection };