"use client";

import OrderList from '../components/OrderList';
import OrdersHeader from '../components/OrdersHeader';

export default function Home() {
	return (
		<main className="min-h-screen container-responsive" style={{ backgroundColor: 'var(--foreground)' }}> 
			<div className="max-w-7xl mx-auto">
				<div className="card-primary space-y-6">
					<OrdersHeader />
					<OrderList />
				</div>
			</div>
		</main>
	);
}