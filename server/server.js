const express = require('express');
const bcrypt = require('bcryptjs');
const cors = require('cors');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3000;
const USERS_FILE = path.join(__dirname, 'users.json');

app.use(cors());
app.use(express.json());

const readUsers = () => {
  if (!fs.existsSync(USERS_FILE)) {
    return [];
  }
  const data = fs.readFileSync(USERS_FILE, 'utf8');
  try {
    return JSON.parse(data);
  } catch {
    return [];
  }
};

const writeUsers = (users) => {
  fs.writeFileSync(USERS_FILE, JSON.stringify(users, null, 2));
};

app.post('/api/register', async (req, res) => {
  const { username, password } = req.body;

  if (!username || !password) {
    return res.status(400).json({ success: false, message: '用户名和密码不能为空' });
  }

  const users = readUsers();

  if (users.find(u => u.username === username)) {
    return res.status(400).json({ success: false, message: '用户名已存在' });
  }

  const hashedPassword = await bcrypt.hash(password, 10);
  const newUser = {
    id: Date.now().toString(),
    username,
    password: hashedPassword,
    createdAt: new Date().toISOString()
  };

  users.push(newUser);
  writeUsers(users);

  res.status(201).json({ success: true, message: '注册成功', user: { id: newUser.id, username: newUser.username } });
});

app.post('/api/login', async (req, res) => {
  const { username, password } = req.body;

  if (!username || !password) {
    return res.status(400).json({ success: false, message: '用户名和密码不能为空' });
  }

  const users = readUsers();
  const user = users.find(u => u.username === username);

  if (!user) {
    return res.status(401).json({ success: false, message: '用户名或密码错误' });
  }

  const isValid = await bcrypt.compare(password, user.password);

  if (!isValid) {
    return res.status(401).json({ success: false, message: '用户名或密码错误' });
  }

  res.json({ success: true, message: '登录成功', user: { id: user.id, username: user.username } });
});

app.post('/api/change-password', async (req, res) => {
  const { username, oldPassword, newPassword } = req.body;

  if (!username || !oldPassword || !newPassword) {
    return res.status(400).json({ success: false, message: '缺少必要参数' });
  }

  const users = readUsers();
  const userIndex = users.findIndex(u => u.username === username);

  if (userIndex === -1) {
    return res.status(404).json({ success: false, message: '用户不存在' });
  }

  const user = users[userIndex];
  const isValid = await bcrypt.compare(oldPassword, user.password);

  if (!isValid) {
    return res.status(401).json({ success: false, message: '旧密码错误' });
  }

  const hashedNewPassword = await bcrypt.hash(newPassword, 10);
  users[userIndex].password = hashedNewPassword;
  writeUsers(users);

  res.json({ success: true, message: '密码修改成功' });
});

app.listen(PORT, () => {
  console.log(`服务器运行在 http://localhost:${PORT}`);
});
