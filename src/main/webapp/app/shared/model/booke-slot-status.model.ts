export interface IBookeSlotStatus {
  id?: number;
  statusName?: string;
}

export class BookeSlotStatus implements IBookeSlotStatus {
  constructor(public id?: number, public statusName?: string) {}
}
