<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>정보 수정 - Yamyam</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user/user.css">
</head>
<body>
    <div class="app-container">
        <nav class="icon-sidebar">
            <a href="${pageContext.request.contextPath}/main" class="sidebar-icon"><i class="fa-solid fa-house"></i></a>
            <a href="${pageContext.request.contextPath}/member?action=mypage" class="sidebar-icon active"><i class="fa-regular fa-user"></i></a>
        </nav>

        <main class="main-content">
            <div class="container user-auth-container">
                <div class="auth-section">
                    <div class="card shadow-sm border-0 rounded-4 p-4 mx-auto" style="max-width: 700px;">
                        <h3 class="text-center mb-4" style="font-weight: 800; color: var(--text-main);">정보 수정</h3>
                        <form action="${pageContext.request.contextPath}/member" method="post">
                            <input type="hidden" name="action" value="update">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">아이디 (변경 불가)</label>
                                    <input type="text" class="form-control bg-light" id="id" name="id" value="${loginUser.id}" readonly>
                                </div>
                                <div class="col-md-6">
                                    <label for="password" class="form-label fw-bold">새 비밀번호</label>
                                    <input type="password" class="form-control" id="password" name="password" placeholder="변경할 경우에만 입력">
                                </div>

                                <hr class="my-4">

                                <div class="col-md-6">
                                    <label for="name" class="form-label fw-bold">이름</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${loginUser.name}" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="birthDate" class="form-label fw-bold">생년월일</label>
                                    <input type="date" class="form-control" id="birthDate" name="birthDate" value="${loginUser.birthDate}" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold d-block">성별</label>
                                    <div class="form-check form-check-inline mt-2">
                                        <input class="form-check-input" type="radio" name="gender" id="editMale" value="male" ${loginUser.gender eq 'male' ? 'checked' : ''}>
                                        <label class="form-check-label" for="editMale">남성</label>
                                    </div>
                                    <div class="form-check form-check-inline mt-2">
                                        <input class="form-check-input" type="radio" name="gender" id="editFemale" value="female" ${loginUser.gender eq 'female' ? 'checked' : ''}>
                                        <label class="form-check-label" for="editFemale">여성</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="height" class="form-label fw-bold">키 (cm)</label>
                                    <input type="number" step="0.1" class="form-control" id="height" name="height" value="${loginUser.height}" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="weight" class="form-label fw-bold">몸무게 (kg)</label>
                                    <input type="number" step="0.1" class="form-control" id="weight" name="weight" value="${loginUser.weight}" required>
                                </div>
                                <div class="col-12">
                                    <label for="disease" class="form-label fw-bold">보유 질환</label>
                                    <input type="text" class="form-control" id="disease" name="disease" value="${loginUser.disease}">
                                </div>
                                <div class="col-12 mt-4 d-flex gap-2">
                                    <button type="submit" class="btn btn-primary flex-grow-1 py-3 fw-bold">저장하기</button>
                                    <a href="${pageContext.request.contextPath}/member?action=mypage" class="btn btn-outline-secondary px-4 d-flex align-items-center text-decoration-none">취소</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>
