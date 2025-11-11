import { createMember, loginMember, findAll, findByUserId, updateMember, removeMember } from "../model/MemberDAO.js";

export const getRegister = (req, res) => {
    res.render('register');
};

export const postRegister = async (req, res) => {
    const { userid, pwd, name } = req.body;
    await createMember(userid, pwd, name);
    res.redirect('/member/login?msg=success');
};

export const getLogin = (req, res) => {
    const msg = req.query.msg || null;
    res.render('login', {msg});
};

export const postLogin = async (req, res) => {
    const { userid, pwd } = req.body;
    const row = await loginMember(userid, pwd);
    const user = row;
    if(user) {
        req.session.user_type = user.user_type;
        req.session.userid = user.userid;
        req.session.username = user.name;
        if(req.session.user_type === 'admin') {
            res.redirect('/member/list');
        } else {
            res.redirect('/member/main');
        }
    } else {
        res.redirect('/member/login?msg=fail');
    }
};

export const logout = (req, res) => {
    req.session.destroy(() => {
        res.redirect('/member/login?msg=logout');
    });
};

export const list = async (req, res) => {
    if(!req.session.userid || req.session.user_type !== 'admin') {
        res.redirect('/member/login?msg=authority');
    }
    const members = await findAll();
    res.render('list', {members});
};

export const main = async (req, res) => {
    if(!req.session.userid) {
        res.redirect('/member/login?msg=authority');
    }
    const member = await findByUserId(req.session.userid);
    res.render('main', {member: member, session: req.session});
};

export const update = async (req, res) => {
    const { userid, name } = req.body;
    await updateMember(userid, name);
    res.redirect('/member/list');
};

export const remove = async(req, res) => {
    const {userid} = req.body;
    await removeMember(userid);
    res.redirect('/member/list');
};