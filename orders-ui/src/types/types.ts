export interface Item {
  name: string;
  image?: string; 
  qty: number;
  cost: number;
  currency: string;
}

export interface Order {
  order: string;
  origin: string;
  total: number;
  createdAt: string;
  items: Item[];
}

export type SupportedCurrency = 'USD' | 'EUR' | 'BRL';

export interface UseOrdersResult {
  orders: Order[];
  loading: boolean;
  error: string | null;
  refetch: () => void;
}