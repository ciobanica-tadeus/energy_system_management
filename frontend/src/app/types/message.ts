export interface Message {
  senderId: string;
  receiverId: string;
  sentAt: Date;
  content: string;
  seen: boolean;
  messageExternalId: string;
}
