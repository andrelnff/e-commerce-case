import React from 'react';
import { LoadingState } from './LoadingSpinner';
import { ErrorState } from './ErrorState';

interface AsyncWrapperProps {
  loading: boolean;
  error: string | null;
  children: React.ReactNode;
  loadingMessage?: string;
  emptyMessage?: string;
  isEmpty?: boolean;
}

const EmptyState = ({ message }: { message: string }) => (
  <div className="text-center py-12">
    <div className="mx-auto w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mb-4">
      <span className="text-4xl" style={{ color: 'var(--primary)' }}>📦</span>
    </div>
    <h3 className="text-lg font-medium mb-2" style={{ color: 'var(--foreground)' }}>
      {message}
    </h3>
  </div>
);

export const AsyncWrapper: React.FC<AsyncWrapperProps> = ({
  loading,
  error,
  children,
  loadingMessage,
  emptyMessage = 'Nenhum item encontrado',
  isEmpty = false,
}) => {
  if (loading) return <LoadingState message={loadingMessage} />;
  if (error) return <ErrorState message={error} />;
  if (isEmpty) return <EmptyState message={emptyMessage} />;
  
  return <>{children}</>;
};

export default AsyncWrapper;
