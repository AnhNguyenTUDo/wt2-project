export class Users {
  name: string;
  password: string;

  static fromObject(object: any): Users {
    const user = new Users();
    user.name = object.name;
    user.password = object.password;
    return user;
  }
}
