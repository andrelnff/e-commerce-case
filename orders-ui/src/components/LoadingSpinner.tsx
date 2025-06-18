import React from 'react';
import { classNames } from '../utils';

interface LoadingSpinnerProps {
  size?: 'sm' | 'md' | 'lg';
  className?: string;
}

const LoadingSpinner: React.FC<LoadingSpinnerProps> = ({ 
  size = 'md', 
  className 
}) => {
  const sizeClasses = {
    sm: 'w-4 h-4',
    md: 'w-8 h-8',
    lg: 'w-12 h-12',
  };
  return (
    <div className={classNames(
      'animate-spin rounded-full border-2 border-gray-300 border-t-current',
      sizeClasses[size],
      className
    )} 
    style={{ borderTopColor: 'var(--primary)' }}
    aria-label="Loading..."
    />
  );
};

export const LoadingState = ({
  message = 'Carregando...', 
  className 
}: {
  message?: string;
  className?: string;
}) => (
  <div className={classNames(
    'flex flex-col items-center justify-center py-12 space-y-4',
    className
  )}>
    <LoadingSpinner size="lg" />
    <p className="text-sm" style={{ color: 'var(--foreground)' }}>
      {message}
    </p>
  </div>
);

export default LoadingSpinner;
