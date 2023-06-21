export class Messages {
  sentOn: Date;
//   headline: string;
  message: string;

  static fromObject(object: any): Messages {
    const n = new Messages();
//     n.headline = object.headline;
    n.message = object.message;
    n.sentOn = new Date(object.sentOn);
    return n;
  }

  get isSentToday(): boolean {
    const now = new Date();
    return now.getDate() === this.sentOn.getDate()
      && now.getMonth() === this.sentOn.getMonth()
      && now.getFullYear() === this.sentOn.getFullYear();
  }
}
