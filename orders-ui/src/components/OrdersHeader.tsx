export default function OrdersHeader() {
  return (
    <div className="border-b pb-6 mb-6" style={{ borderColor: '#e5e7eb' }}>
      <h1 className="text-3xl sm:text-4xl md:text-5xl font-bold tracking-tight" style={{ color: 'var(--foreground)' }}>
        Dashboard de Pedidos
      </h1>
      <p className="text-base sm:text-lg mt-3" style={{ color: 'var(--foreground)', opacity: 0.8 }}>
        Visualize e gerencie todos os seus pedidos
      </p>
    </div>
  );
}