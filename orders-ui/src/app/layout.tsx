import "./globals.css";
import ApolloWrapper from "../components/ApolloWrapper";
import ErrorBoundary from "../components/ErrorBoundary";
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: {
    default: 'E-commerce Orders',
    template: '%s | E-commerce Orders'
  },
  description: 'Sistema de gerenciamento de pedidos para e-commerce',
  keywords: ['e-commerce', 'orders', 'management', 'pedidos'],
  authors: [{ name: 'E-commerce Team' }],
  viewport: 'width=device-width, initial-scale=1',
  robots: 'index, follow',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en" className="h-full">
      <body className="h-full antialiased" style={{ 
        backgroundColor: 'var(--background)', 
        color: 'var(--foreground)',
        fontFamily: 'var(--font-sans)'
      }}>
        <ErrorBoundary>
          <ApolloWrapper>
            {children}
          </ApolloWrapper>
        </ErrorBoundary>
      </body>
    </html>
  );
}
