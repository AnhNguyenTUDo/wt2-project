export class User {
  username: string='';
  password: string='';

  static fromObject(object: any): User {
    const user = new User();
    user.username = object.name;
    user.password = object.password;
    return user;
  }
}
