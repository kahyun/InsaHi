export async function fetcher(url: string, options: RequestInit = {}) {
    const token = localStorage.getItem("accessToken");

    const headers = {
        "Content-Type": "application/json",
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
        ...options.headers,
    };

    const response = await fetch(url, {
        ...options,
        headers,
    });

    if (!response.ok) {
        if (response.status === 401) {
            alert("세션이 만료되었습니다. 다시 로그인하세요.");
            localStorage.removeItem("accessToken");
            window.location.href = "/";
        }
        throw new Error("API 요청 실패");
    }

    return response.text();
}
