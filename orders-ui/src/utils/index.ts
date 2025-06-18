import { SupportedCurrency } from '../types/types';

export const isValidImageUrl = (url: string | undefined): boolean => {
  if (!url) return false;
  
  try {
    new URL(url);
    return true;
  } catch {
    return false;
  }
};

export const getValidImageUrl = (url: string | undefined, fallback: string): string => {
  return isValidImageUrl(url) ? url! : fallback;
};

export const formatCurrency = (
  amount: number, 
  currency: SupportedCurrency | string = 'USD',
  locale: string = 'en-US'
): string => {
  return new Intl.NumberFormat(locale, {
    style: 'currency',
    currency: currency,
  }).format(amount);
};

export const classNames = (...classes: (string | undefined | null | false)[]): string => {
  return classes.filter(Boolean).join(' ');
};
