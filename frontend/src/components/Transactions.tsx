import { Account } from "./Account";
import { TransactionType } from "./TransactionType";

export interface Transaction {
  id: number | undefined;
  toAccount: Account | undefined;
  fromAccount: Account | undefined;
  type: TransactionType | undefined;
  amount: number | undefined;
  description: string | undefined;
  datetime: Date | undefined;
}
