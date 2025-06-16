// vite.config.js
import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

export default defineConfig({
  build: {
    outDir: 'dist'
  },
  plugins: [
    vue(),
    vueDevTools(),
  ],
  server: {
    port: 90,
    host: true,
    proxy: {
      '/backend-service': {
        target: 'http://api-gateway:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/backend-service/, '/backend'),
        // Optional verbose logging for debug
        configure: (proxy, options) => {
          proxy.on('proxyReq', (proxyReq, req, res) => {
            console.log('Proxying request:', req.method, req.url);
          });
          proxy.on('error', (err, req, res) => {
            console.error('Proxy error:', err);
          });
        },
      },
    },
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
})
