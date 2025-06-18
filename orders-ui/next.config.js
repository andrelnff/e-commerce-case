/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'standalone',
  async rewrites() {
    const isDev = process.env.NODE_ENV === 'development';
    const baseUrl = isDev ? 'http://localhost:8080' : 'http://consumer-service:8080';
    
    return [
      {
        source: '/graphql',
        destination: `${baseUrl}/graphql`,
      },
    ];
  },
};

module.exports = nextConfig;