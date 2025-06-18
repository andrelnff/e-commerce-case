import { useQuery } from '@apollo/client';
import { GET_ORDERS } from '../graphql/queries';
import { UseOrdersResult } from '../types/types';

export const useOrders = (): UseOrdersResult => {
  const { data, loading, error, refetch } = useQuery(GET_ORDERS, {
    errorPolicy: 'all',
    notifyOnNetworkStatusChange: true,
  });

  return {
    orders: data?.placedOrders || [],
    loading,
    error: error?.message || null,
    refetch,
  };
};
