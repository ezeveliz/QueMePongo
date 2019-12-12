importScripts('https://storage.googleapis.com/workbox-cdn/releases/4.3.1/workbox-sw.js');

if (workbox) {
    console.log(`Yay! Workbox is loaded 🎉`);
} else {
    console.log(`Boo! Workbox didn't load 😬`);
}

workbox.core.skipWaiting();
workbox.core.clientsClaim();

// fonts
workbox.routing.registerRoute(
    new RegExp('.(?:woff2|woff|ttf|eot)$'),
    new workbox.strategies.CacheFirst({
        cacheName: 'fonts',
        plugins: [
            new workbox.cacheableResponse.Plugin({
                statuses: [0, 200]
            })
        ]
    })
);

// google stuff
workbox.routing.registerRoute(
    new RegExp('.*(?:googleapis|gstatic).com.*$'),
    new workbox.strategies.CacheFirst({
        cacheName: 'google'
    })
);

// static
workbox.routing.registerRoute(
    new RegExp('.(?:js|css|ico)$'),
    new workbox.strategies.CacheFirst({
        cacheName: 'static',
        plugins: [
            new workbox.expiration.Plugin({
                maxAgeSeconds: 7 * 24 * 60 * 60,
            })
        ]
    }),
);

// images
workbox.routing.registerRoute(
    new RegExp('.(?:jpg|png|gif|svg)$'),
    new workbox.strategies.CacheFirst({
        cacheName: 'images',
        plugins: [
            new workbox.expiration.Plugin({
                maxEntries: 30,
                purgeOnQuotaError: true,
                maxAgeSeconds: 28 * 24 * 60 * 60,
            })
        ]
    })
);

workbox.precaching.precacheAndRoute([
    {
        url: 'offline'
    }
]);

/**
 * save pages to cache on visit & serve when offline
 * or if not cached then serve the "offline view"
 */
const customHandler = async (args) => {
    try {
        return await new workbox.strategies.NetworkFirst({
            cacheName: 'pages',
            plugins: [
                new workbox.expiration.Plugin({
                    maxEntries: 20,
                    purgeOnQuotaError: true
                })
            ]
        }).handle(args) || caches.match('/offline')
    } catch (error) {
        return caches.match('/offline')
    }
};

const navigationRoute = new workbox.routing.NavigationRoute(customHandler, {
    // dont cache this urls
    blacklist: [
        new RegExp('/(login|register|password|auth)'),
        new RegExp('/admin')
    ]
});

workbox.routing.registerRoute(navigationRoute);