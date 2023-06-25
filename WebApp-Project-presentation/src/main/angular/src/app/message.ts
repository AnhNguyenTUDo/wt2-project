export class Message {

  id: string = '';
  content: string ='';
  sender: string ='';

  static fromObject(object: any): Message {
      const msg = new Message();
      msg.id = object.id;
      msg.content = object.content;
      msg.sender = object.sender;
      return msg;
    }


}
