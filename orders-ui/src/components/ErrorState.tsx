import { classNames } from '../utils';

export const ErrorState = ({ 
  message = 'Algo deu errado. Tente novamente.', 
  className 
}: { 
  message?: string; 
  className?: string; 
}) => (
  <div className={classNames(
    'flex flex-col items-center justify-center py-12 space-y-4 text-center',
    className
  )}>
    <div className="text-red-500 text-4xl mb-2">⚠️</div>
    <h3 className="text-lg font-semibold" style={{ color: 'var(--foreground)' }}>
      Ops! Ocorreu um erro
    </h3>
    <p className="text-sm text-gray-600 max-w-md">
      {message}
    </p>
  </div>
);

export default ErrorState;
